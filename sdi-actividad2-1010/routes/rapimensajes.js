module.exports = function (app, gestorBD) {

    app.post("/api/message/send", function (req, res) {
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
                var sale = {_id: gestorBD.mongo.ObjectID(req.body.idSale)};
                gestorBD.obtenerOfertas(sale, function (ofertas) {
                    if (ofertas === null || ofertas.length === 0) {
                        res.status(204); // Unauthorized
                        res.json({
                            error: "No puedes enviar un mensaje a esa oferta"
                        });
                    } else {
                        var sale = ofertas[0];

                        var criterio = {
                            sale: gestorBD.mongo.ObjectID(req.body.idSale),
                            receiver: req.body.receiver,
                            sender:
                            usuario,
                            message:
                            req.body.message,
                            valid:
                                true,
                            read:
                                false,
                            date:
                                new Date()
                        }

                        //si el recibidor es nulo es que el que está enviando el mensaje es
                        //el propietario de la oferta

                        gestorBD.enviarMensaje(criterio, function (id) {
                            if (id === null) {

                                res.status(204); // Unauthorized
                                res.json({
                                    error: "No se pudo enviar el mensaje"
                                });
                            } else {

                                res.status(200);
                                res.send(JSON.stringify(criterio));
                            }
                        });

                    }
                });

            }
        })
    });

    // ver la conversación para el usuario que no es dueño de la oferta
    app.post("/api/conversation/:idSale", function (req, res) {
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
                gestorBD.obtenerOfertas({_id: gestorBD.mongo.ObjectID(req.params.idSale)}, function (oferta) {
                    if (oferta === null || oferta.length === 0) {
                        res.status(204); // Unauthorized
                        res.json({
                            error: "La oferta no existe"
                        });
                    } else {
                        var criterio;
                        var usuarioReceiver = req.body.usuarioReceiver;
                        //el usuario no es el dueño de la oferta
                        if (usuarioReceiver === null) {
                            criterio = {
                                $or: [{$and: [{sender: usuario}, {receiver: oferta[0].owner}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]},
                                    {$and: [{sender: oferta[0].owner}, {receiver: usuario}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]}
                                ]
                            }
                        } else {
                            //el usuario es el dueño de la oferta
                            criterio = {
                                $or: [{$and: [{sender: usuario}, {receiver: usuarioReceiver}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]},
                                    {$and: [{sender: usuarioReceiver}, {receiver: usuario}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]}
                                ]
                            }
                        }
                        gestorBD.obtenerConversacion(criterio, function (mensajes) {
                            if (mensajes === null || mensajes.length === 0) {
                                res.status(204); // Unauthorized
                                res.json({
                                    error: "No se pudo abrir la conversación"
                                });

                            } else {
                                res.send(JSON.stringify(mensajes));
                            }
                        })
                    }
                })
            }
        })
    });

    app.post("/api/message/delete/:idSale", function (req, res) {
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
                gestorBD.obtenerOfertas({_id: gestorBD.mongo.ObjectID(req.params.idSale)}, function (oferta) {
                    if (oferta === null || oferta.length === 0) {
                        res.status(204); // Unauthorized
                        res.json({
                            error: "La oferta no existe"
                        });
                    } else {
                        var criterio;
                        var usuarioReceiver = req.body.usuarioReceiver;
                        //el usuario no es el dueño de la oferta
                        if (usuarioReceiver === null) {
                            criterio = {
                                $or: [{$and: [{sender: usuario}, {receiver: oferta[0].owner}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]},
                                    {$and: [{sender: oferta[0].owner}, {receiver: usuario}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]}
                                ]
                            }
                        } else {
                            //el usuario es el dueño de la oferta
                            criterio = {
                                $or: [{$and: [{sender: usuario}, {receiver: usuarioReceiver}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]},
                                    {$and: [{sender: usuarioReceiver}, {receiver: usuario}, {sale: gestorBD.mongo.ObjectID(req.params.idSale)}]}
                                ]
                            }
                        }
                        gestorBD.deleteConversacion(criterio, function (mensajes) {
                            if (mensajes === null || mensajes.length === 0) {
                                res.status(204); // Unauthorized
                                res.json({
                                    error: "No se pudo abrir la conversación"
                                });

                            } else {
                                res.send(JSON.stringify(mensajes));
                            }
                        })
                    }
                })
            }
        })
    });

    app.get("/api/message/read/:id", function (req, res) {

        var criterio = {
            _id: gestorBD.mongo.ObjectID(req.params.id),

        }
        gestorBD.marcarMensajeLeido(criterio, function (mensajes) {
            if (mensajes === null || mensajes.length === 0) {
                res.status(500);
                res.json({
                    error: "No se pudo marcar como leído el mensaje"
                })
            } else {
                res.status(200);
                res.send(JSON.stringify(mensajes));
            }
        })
    });
};