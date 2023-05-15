module.exports = function (app, db, storage) {
    app.get('/exhibitions/list', async (req, res) => {
        try {
            var list = await db.collection('exhibitions').get();
            res.send({ status: "success", message: list.docs.map(doc => doc.data()) });
        } catch (e) {
            res.send({ status: "error", message: e });
        }
    });


    app.post('/exhibitions/add', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('exhibitions').add(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }

    });

    app.post('/exhibitions/update', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('exhibitions').doc(item.id).update(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });

    app.post('/exhibitions/delete', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('exhibitions').doc(item.id).delete();
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });
}