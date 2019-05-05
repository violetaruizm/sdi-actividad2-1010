module.exports = function (app, gestorBD) {
    app.post("/api/autenticar/", function (req, res) {
        let seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');
        let criterio = {
            email: req.body.email,
            password: seguro
        };
        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios == null || usuarios.length === 0) {
                res.status(401); // Unauthorized
                app.get('logger').error('API autenticación: El usuario no pudo autenticarse');
                res.json({
                    autenticado: false
                })
            } else {
                let token = app.get('jwt').sign(
                    {usuario: criterio.email, tiempo: Date.now() / 1000},
                    "secreto");
                res.status(200);
                app.get('logger').info('API autenticación: El usuario se autenticó correctamente');
                res.json({
                    autenticado: true,
                    token: token
                });
            }

        });
    });
};
