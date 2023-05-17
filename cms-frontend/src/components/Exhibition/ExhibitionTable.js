import React, { useEffect, useState } from 'react';
import { Table } from 'react-bootstrap';

function ExhibitionTable () {
    const [data, setJsonData] = useState([]);
    console.log(data);
    
    useEffect(() => {
      // Make API call to fetch data
      // Replace the API_URL with your actual API endpoint
      fetch('http://localhost:3001/exhibitions/list')
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
    function handleDelete (index, item) {
        
    }
    return (
  
      <Table striped bordered hover style={tableStyles}>
        <thead>
          <tr>
            <th style={{ width: '10%' }}>Exihibition Name</th>
            <th style={{ width: '20%' }}>Museum Name</th>
            <th style={{ width: '10%' }}>Place</th>
            <th>Thumbnail</th>
            <th>Delete Exhibition</th>
          </tr>
        </thead>
        <tbody>
          {
              data.map((item, index) => (
                  <tr key={index}>
                      <td>{item.name}</td>
                      <td style={{ textAlign: 'left' }}>{item.museumName}</td>
                      <td>{item.place}</td>
                      <td>
                      <img style={{ width: 300, height: 250 }} src={item.thumbnail} alt="Non Thumbnail" />
                      </td>
                      <td>
                          <button onClick={() => handleDelete(index, item)}>Delete</button>
                      </td>
                  </tr>))
          }
        </tbody>
      </Table>
      
    );
}

export { ExhibitionTable }