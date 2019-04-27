module.exports = function(app,gestorBD){
    app.post("/api/send/:idSale", function (req, res) {
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
                var sale = {_id : gestorBD.mongo.ObjectID(req.params.idSale)};
                gestorBD.obtenerOfertas(sale,function (ofertas){
                    if(ofertas===null || ofertas.length===0){
                        res.status(204); // Unauthorized
                        res.json({
                            err: "No puedes enviar un mensaje a esa oferta"
                        });
                    }else{

                        var criterio = {
                            sale : gestorBD.mongo.ObjectID(req.params.idSale),
                            sender:  usuario,
                            message : req.body.message,
                            valid: true,
                            read : false,
                            date : new Date()
                        }
                        gestorBD.enviarMensaje(criterio, function (id) {
                            if (id===null) {

                                res.status(204); // Unauthorized
                                res.json({
                                    err: "No se pudo enviar el mensaje"
                                });
                            } else {

                                res.status(200);
                                res.send(JSON.stringify(criterio));
                            }
                        });

                    }
                });

            }
        })});
}