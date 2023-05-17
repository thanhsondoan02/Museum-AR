import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '..';
import { useNavigate } from 'react-router-dom';
function ModifyExhibition () {
    const [name, setExihibitionName] = useState('');
    const [museumName, setMuseumName] = useState('');
    const [place, setPlace] = useState('');
    const [thumbnail, setThumbnail] = useState('');

    const navigate = useNavigate();
    const [selectedNameId, setSelectedNameId] = useState('');
    const [list_data, setJsonData] = useState([]);

    useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/exhibitions/list')
    .then(response => response.json())
    .then(list_data => { 
            setJsonData(list_data.message);
        
            console.log(list_data.message); 
        })
    .catch(error => console.log(error));
    }, []);

  const extractedNames = list_data.map((item) => item.name);

  const handleModifySubmit = (e) => {
    e.preventDefault();
    
    // Create the payload to send to the backend API
    const payload = {
      id : selectedNameId,
      name,
      museumName,
      place,
      thumbnail,
    };

    console.log(payload);
    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:3001/exhibitions/update', {
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
      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });

      console.log('Modify button clicked');
      navigate('/exhibitions');
  };

    const handleNameChange = (e) => {
        const selectedName = e.target.value;
        setExihibitionName(selectedName);
        const selectedOption = list_data.find((item) => item.name === selectedName);
        setSelectedNameId(selectedOption.id);
    }


  return (
    <div>
        <NavigationBar />
        <Container className="d-flex justify-content-center">
          <div className="w-50">
            
            <Form onSubmit={handleModifySubmit} className="mt-3">
            <Form.Group>
              <Form.Label>Exhibition Name:</Form.Label>
              <Form.Control as="select" onChange={handleNameChange}>
                {extractedNames.map((name) => (
                  <option key={name} value={name}>
                    {name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>

            <Form.Group controlId="museumName">
                <Form.Label>Museum Name:</Form.Label>
                <Form.Control
                    type="text"
                    value={museumName}
                    onChange={(e) => setMuseumName(e.target.value)}
                />
            </Form.Group>

            <Form.Group controlId="Place">
                <Form.Label>Place:</Form.Label>
                <Form.Control
                    type="text"
                    value={place}
                    onChange={(e) => setPlace(e.target.value)}
                />
            </Form.Group>

            <Form.Group controlId="thumbnail">
            <Form.Label>Thumbnail:</Form.Label>
            <Form.Control
              type="text"
              value={thumbnail}
              onChange={(e) => setThumbnail(e.target.value)}
            />
            </Form.Group>

            <Button variant="dark" type="submit" className='mt-3' onClick={handleModifySubmit}>
                Modify Exhibition
            </Button>
            </Form>
          </div>
        </Container>
    </div>
    
  );
};

export { ModifyExhibition } ;
