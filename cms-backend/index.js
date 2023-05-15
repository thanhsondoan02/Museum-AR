require('dotenv').config();

const express = require('express');
const { db, storage } = require('./firebase');
const bodyParser = require("body-parser")

const app = express();
const port = process.env.PORT || 3001;

app.use(bodyParser.json({ limit: '2048mb' }))
app.use(bodyParser.urlencoded({ limit: '2048mb', extended: true, parameterLimit: 2048000 }))

require('./api/items.js')(app, db, storage);
require('./api/collections.js')(app, db, storage);
require('./api/exhibitions.js')(app, db, storage);
require('./api/stories.js')(app, db, storage);

app.listen(port, () => {
    console.log(`[App] Listening at port:${port}`);
});