import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '../../components';
import Axios from "axios"
const AddItem = () => {
  const [name, setItemName] = useState('');
  const [creatorName, setCreatorName] = useState('');
  const [description, setDescription] = useState('');
  const [time, setDateCreated] = useState('');
  const [collectionId, setCollectionId] = useState('');
  const [collection, setCollectionName] = useState('');
  const [thumbnail, setThumbnail] = useState('');
  const [file, setSelectedFile] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();

    // Create the payload to send to the backend API
    const payload = new FormData();

    payload.append('file', file);
    payload.append('requests', JSON.stringify({
      name: name,
      creatorName: creatorName,
      description: description,
      time: time,
      collectionId: collectionId,
      collection: collection,
      thumbnail: thumbnail
    }));

    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    console.log(payload)
    Axios.post(`http://localhost:3001/items/add/`, payload, { withCredentials: true, headers: { "Content-Type": "multipart/form-data" } })
      .then((response) => response.json())
      .then((data) => {
        // Handle the response from the API
        console.log('API response:', data);
      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
    
  };

  return (
    <div>
        <NavigationBar />
    <Container className="d-flex justify-content-center">
      <div className="w-50">
        <h1></h1>
        <Form onSubmit={handleSubmit}>
            <Form.Group controlId="name">
                <Form.Label>Item Name:</Form.Label>
                <Form.Control 
                    type="text"
                    value={name}
                    onChange={(e) => setItemName(e.target.value)}
                />
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

            <Form.Group controlId="collectionId">
                <Form.Label>Collection ID:</Form.Label>
                <Form.Control
                    type="text"
                    value={collectionId}
                    onChange={(e) => setCollectionId(e.target.value)}
                />
            </Form.Group>

            <Form.Group controlId="collection">
                <Form.Label>Collection Name:</Form.Label>
                <Form.Control
                    type="text"
                    value={collection}
                    onChange={(e) => setCollectionName(e.target.value)}
                />
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
                <Form.Control type="file" onChange={handleFileChange} />
            </Form.Group>
            <Button variant="primary" type="submit" className='mt-3'>
                Add Item
            </Button>
            <Button variant="primary" type="submit" className='mt-3 ms-3'>
                Modify Item
            </Button>
        </Form>
      </div>
    </Container>
    </div>
    
  );
};

export { AddItem } ;
