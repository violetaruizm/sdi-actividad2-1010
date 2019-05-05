module.exports = function (app, gestorBD) {
    app.get("/api/sales", function (req, res) {
        let criterio = {
            owner: {$ne: res.usuario},
            valid: true,
            onsale: true
        };
        gestorBD.obtenerOfertas(criterio, function (ofertas) {
            if (ofertas == null || ofertas.length === 0) {

                res.status(204); // Unauthorized
                app.get('logger').error('API ofertas: No se encontraron ofertas');
                res.json({
                    err: "No hay ofertas disponibles"
                });
            } else {

                res.status(200);
                app.get('logger').info('API ofertas: se obtuvieron correctamete todas las ofertas del sistema');
                res.send(ofertas);
            }
        });
    });

    app.get("/api/propias", function (req, res) {
        let criterio = {
            owner:  res.usuario,
            valid: true,

        };
        console.log(criterio);
        gestorBD.obtenerOfertas(criterio, function (ofertas) {
            if (ofertas == null ) {

                res.status(204); // Unauthorized
                res.json({
                    err: "No hay ofertas disponibles"
                });
            } else {

                res.status(200);
                res.send(ofertas);
            }
        });
    });
};
