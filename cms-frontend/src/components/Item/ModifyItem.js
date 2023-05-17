import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '..';
import { useNavigate } from 'react-router-dom';
function ModifyItem () {
    const [id, setId] = useState('');
    const [name, setItemName] = useState('');
    const [creatorName, setCreatorName] = useState('');
    const [description, setDescription] = useState('');
    const [time, setDateCreated] = useState('');
    const [collectionId, setCollectionId] = useState('');
    const [collection, setCollectionName] = useState('');
    const [thumbnail, setThumbnail] = useState('');
    const [file, setSelectedFile] = useState(null);

    const navigate = useNavigate();

    const [list_data, setJsonData] = useState([]);
    const [idData, setJsonIdData] = useState([]);

    useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/collections/list')
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
    const payload = new FormData();

    payload.append('file', file);
    payload.append('requests', JSON.stringify({
      id: id,
      name: name,
      creatorName: creatorName,
      description: description,
      time: time,
      collectionId: collectionId,
      collection: collection,
      thumbnail: thumbnail
    }));

    console.log(payload);
    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:3001/items/update', {
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
      navigate('/items');
  };

  const handleNameChange = (e) => {
      const selectedName = e.target.value;
      setCollectionName(selectedName);
      const selectedOption = list_data.find((item) => item.name === selectedName);
      setCollectionId(selectedOption.id);
      console.log(collectionId)
  }


  useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/items/list')
    .then(response => response.json())
    .then(idData => { 
            setJsonIdData(idData.message);
        
            console.log(idData.message); 
        })
    .catch(error => console.log(error));
  }, []);
  const extractedIdNames = idData.map((item) => item.name);

  const handleIdNameChange = (e) => {
      const selectedIdName = e.target.value;
      setItemName(selectedIdName);
      const selectedOptionId = idData.find((item) => item.name === selectedIdName);
     
      setId(selectedOptionId.id)
      console.log(id);
  }

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
    
  };

  return (
    <div>
        <NavigationBar />
        <Container className="d-flex justify-content-center">
          <div className="w-50">
            
            <Form onSubmit={handleModifySubmit} className="mt-3">
              <Form.Group>
                <Form.Label>Item Name:</Form.Label>
                <Form.Control as="select" onChange={handleIdNameChange}>
                  {extractedIdNames.map((name) => (
                    <option key={name} value={name}>
                      {name}
                    </option>
                  ))}
                </Form.Control>
              </Form.Group>

              <Form.Group controlId="creatorName">
                  <Form.Label>Creator Name:</Form.Label>
                  <Form.Control
                      type="text"
                      value={creatorName}
                      onChange={(e) => setCreatorName(e.target.value)}
                  />
              </Form.Group>

              <Form.Group controlId="description">
                  <Form.Label>Description:</Form.Label>
                  <Form.Control
                      type="text"
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                  />
              </Form.Group>
              
            <Form.Group>
              <Form.Label>Collection Name:</Form.Label>
              <Form.Control as="select" onChange={handleNameChange}>
                {extractedNames.map((name) => (
                  <option key={name} value={name}>
                    {name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
            
            <Form.Group controlId="time">
            <Form.Label>Date Created:</Form.Label>
            <Form.Control
              type="text"
              value={time}
              onChange={(e) => setDateCreated(e.target.value)}
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

            <Form.Group controlId="file">
            <Form.Label>File:</Form.Label>
            <Form.Control
              type="file"
              onChange={handleFileChange}
            />

            </Form.Group>
            <Button variant="dark" type="submit" className='mt-3' onClick={handleModifySubmit}>
                Modify Item
            </Button>
            </Form>
          </div>
        </Container>
    </div>
    
  );
};

export { ModifyItem } ;
