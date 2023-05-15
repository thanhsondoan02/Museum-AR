module.exports = function (app, db, storage) {
    app.get('/collections/list', async (req, res) => {
        try {
            var list = await db.collection('collections').get();
            res.send({ status: "success", message: list.docs.map(doc => doc.data()) });
        } catch (e) {
            res.send({ status: "error", message: e });
        }
    });

    app.post('/collections/add', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('collections').add(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });

    app.post('/collections/update', async (req, res) => {
        var item = req.body;
        try {
            var result = await db.collection('collections').doc(item.id).update(item);
            res.send({ status: "success", message: result.id });
        }
        catch (e) {
            res.send({ status: "error", message: e });
        }
    });
}