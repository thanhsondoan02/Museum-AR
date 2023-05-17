import React, { useEffect, useState } from 'react';
import { Table, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
const ItemTable = () => {
  const [data, setJsonData] = useState([]);
  console.log(data);
  const navigate = useNavigate();
  const fetchData = () => {
    // Fetch data from the backend API to populate the table
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:3001/item/list')
      .then(response => response.json())
      .then(data => { 
            setJsonData(data.message);
        
            console.log(data.message); 
        })
      .catch(error => console.log(error));
  };

  

  useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/items/list')
      .then(response => response.json())
      .then(data => { 
            setJsonData(data.message);
        
            console.log(data.message); 
        })
      .catch(error => console.log(error));
  }, []);

  const tableStyles = {
    fontFamily: 'Source Sans Pro, sans-serif',
    fontSize: '14px',
    textAlign: 'center',
    border: '0.5px solid',
    width: '95%',
    margin: 'auto',
    marginTop: '30px'
  }
  const handleDelete = (e) => {
      
    // Create the payload to send to the backend API
    const payload = {
      id : e.id,
      model3d_id: e.model3d_id,
    };

    console.log("send payload:", payload);
    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    
    fetch('http://localhost:3001/items/delete', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })
      .then((response) => response.json())
      .then((data) => {
        // Handle the response from the API
        console.log('API response:', data);
        fetchData();

        // clearTimeout (timeoutId);
      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });
      navigate('/items');

  };
  useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/items/list')
      .then(response => response.json())
      .then(data => { 
            setJsonData(data.message);
        
            console.log(data.message); 
        })
      .catch(error => console.log(error));
  }, []);
  return (

    <Table striped bordered hover style={tableStyles}>
      <thead>
        <tr>
          <th style={{ width: '10%' }}>Item Name</th>
          <th style={{ width: '10%' }}>Creator Name</th>
          <th style={{ width: '30%' }}>Description</th>
          <th>Date Created</th>
          <th>Collection ID</th>
          <th>Thumbnail</th>
          <th>Delete Item</th>
        </tr>
      </thead>
      <tbody>
        {
            data.map((item, index) => (
                <tr key={index}>
                    <td>{item.name}</td>
                    <td>{item.creatorName}</td>
                    <td style={{ textAlign: 'left' }}>{item.description}</td>
                    <td>{item.time}</td>
                    <td>{item.collectionId}</td>
                    <td>
                    <img style={{ width: 100, height: 150 }} src={item.thumbnail} alt="Non Image" />
                    </td>
                    <td>
                      <Button variant="dark" onClick={() => handleDelete(item)}>
                        Delete
                      </Button>
                    </td>
                </tr>))
        }
      </tbody>
    </Table>
    
  );
};

export { ItemTable }