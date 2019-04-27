module.exports = function (app, gestorBD) {

    app.get("/api/sales", function (req, res) {
        var token = req.headers['token'] || req.body.token || req.query.token;
        app.get('jwt').verify(token, 'secreto', function (err, infoToken) {
            if (err) {

                res.status(403); // Forbidden
                res.json({
                    acceso: false,
                    error: 'El token no es válido'
                });

            } else {
                // dejamos correr la petición
                var usuario = infoToken.usuario;
                criterio = {
                    owner: {$ne: res.usuario},
                    valid: true,
                    onsale : true
                }
                gestorBD.obtenerOfertas(criterio, function (ofertas) {
                    if (ofertas == null || ofertas.length === 0) {

                        res.status(204); // Unauthorized
                        res.json({
                            err: "No hay ofertas disponibles"
                        });
                    } else {

                        res.status(200);
                        res.send(ofertas);
                    }
                });
            }
        })});}