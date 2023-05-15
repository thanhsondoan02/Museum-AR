module.exports = function (app, db, storage) {
    app.get('/stories/list', async (req, res) => {
        try {
            var list = await db.collection('stories').get();
            res.send({ status: "success", message: list.docs.map(doc => doc.data()) });
        } catch (e) {
            res.send({ status: "error", message: e });
        }
    });


    app.post('/stories/add', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('stories').add(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }

    });

    app.post('/stories/update', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('stories').doc(item.id).update(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });

    app.post('/stories/delete', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('stories').doc(item.id).delete();
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });
}