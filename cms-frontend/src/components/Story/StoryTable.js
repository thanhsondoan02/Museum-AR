import React, { useEffect, useState } from 'react';
import { Table, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
function StoryTable () {
    const [data, setJsonData] = useState([]);
    const navigate = useNavigate();
    const fetchData = () => {
      // Fetch data from the backend API to populate the table
      // Replace the URL with your actual API endpoint
      fetch('http://localhost:3001/stories/list')
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
      fetch('http://localhost:3001/stories/list')
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
        id : e,
      };

      console.log(payload);
      // Send the payload to the backend API
      // Replace the URL with your actual API endpoint
      
      fetch('http://localhost:3001/stories/delete', {
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
        navigate('/stories');

    };
    useEffect(() => {
      // Make API call to fetch data
      // Replace the API_URL with your actual API endpoint
      fetch('http://localhost:3001/stories/list')
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
            <th style={{ width: '10%' }}>Title</th>
            <th style={{ width: '20%' }}>Description</th>
            <th style={{ width: '10%' }}>Collection</th>
            <th>Thumbnail</th>
            <th>Delete Story</th>
          </tr>
        </thead>
        <tbody>
          {
              data.map((item, index) => (
                  <tr key={index}>
                      <td>{item.title}</td>
                      <td style={{ textAlign: 'left' }}>{item.description}</td>
                      <td>{item.collectionId}</td>
                      <td>
                      <img style={{ width: 300, height: 250 }} src={item.thumbnail} alt="Non Thumbnail" />
                      </td>
                      <td>
                        <Button variant="dark" onClick={() => handleDelete(item.id)}>
                          Delete
                        </Button>
                      </td>
                      
                  </tr>))
          }
        </tbody>
      </Table>
      
    );
}

export { StoryTable }