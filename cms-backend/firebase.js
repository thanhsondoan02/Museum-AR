require('dotenv').config();

const { initializeApp, cert } = require('firebase-admin/app');
const { getFirestore } = require('firebase-admin/firestore');
const { getStorage } = require('firebase-admin/storage');

const serviceAccount = require('./creds.json');

const app = initializeApp({
    credential: cert(serviceAccount),
    storageBucket: 'gs://museum-ar-32277.appspot.com',
});

const db = getFirestore();
const storage = getStorage(app);

module.exports = { db, storage };