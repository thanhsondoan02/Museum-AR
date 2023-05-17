import React, { useEffect, useState } from 'react';
import { Table } from 'react-bootstrap';

const ItemTable = () => {
  const [data, setJsonData] = useState([]);
  console.log(data);
  
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
  function handleDelete(index, item) {
    
  }
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
                        <button onClick={() => handleDelete(index, item)}>Delete</button>
                    </td>
                </tr>))
        }
      </tbody>
    </Table>
    
  );
};

export { ItemTable }