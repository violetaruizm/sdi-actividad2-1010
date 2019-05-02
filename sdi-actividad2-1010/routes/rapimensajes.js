module.exports = function (app, gestorBD) {

    function getOferta(id, functionCallBack) {
        let sale = {
            _id: gestorBD.mongo.ObjectID(id)
        };
        gestorBD.obtenerOfertas(sale, function (ofertas) {
            functionCallBack(ofertas);
        });
    }

    app.post("/api/message/send", function (req, res) {
        let usuario = res.usuario;
        getOferta(req.body.idSale,
            function (ofertas) {
                if (ofertas === null || ofertas.length === 0) {
                    res.status(204); // Unauthorized
                    res.json({
                        error: "No puedes enviar un mensaje a esa oferta"
                    });
                } else {
                    let criterio = {
                        sale: gestorBD.mongo.ObjectID(req.body.idSale),
                        receiver: req.body.receiver,
                        sender: usuario,
                        message: req.body.message,
                        valid: true,
                        read: false,
                        date: new Date()
                    };
                    // si el recibidor es nulo es que el que está enviando el mensaje es
                    // el propietario de la oferta
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
    });

    function getMensajesConversacion(res, req, functionCallBack) {
        let usuario = res.usuario;
        getOferta(req.params.idSale, function (oferta) {
            if (oferta === null || oferta.length === 0) {
                res.status(204); // Unauthorized
                res.json({
                    error: "La oferta no existe"
                });
            } else {
                let criterio;
                let usuarioReceiver = req.body.usuarioReceiver;
                //el usuario no es el dueño de la oferta
                if (usuarioReceiver === null) {
                    criterio = {
                        $or: [
                            {
                                $and: [
                                    {sender: usuario},
                                    {receiver: oferta[0].owner},
                                    {sale: gestorBD.mongo.ObjectID(req.params.idSale)}
                                ]
                            },
                            {
                                $and: [
                                    {sender: oferta[0].owner},
                                    {receiver: usuario},
                                    {sale: gestorBD.mongo.ObjectID(req.params.idSale)}
                                ]
                            }
                        ]
                    }
                } else {
                    //el usuario es el dueño de la oferta
                    criterio = {
                        $or: [
                            {
                                $and: [
                                    {sender: usuario},
                                    {receiver: usuarioReceiver},
                                    {sale: gestorBD.mongo.ObjectID(req.params.idSale)}
                                ]
                            },
                            {
                                $and: [
                                    {sender: usuarioReceiver},
                                    {receiver: usuario},
                                    {sale: gestorBD.mongo.ObjectID(req.params.idSale)}
                                ]
                            }
                        ]
                    }
                }
                functionCallBack(criterio);
            }
        })
    }

    // ver la conversación para el usuario que no es dueño de la oferta
    app.post("/api/conversation/:idSale", function (req, res) {
        getMensajesConversacion(res, req, function (criterio) {
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
        });
    });

    app.post("/api/message/delete/:idSale", function (req, res) {
        getMensajesConversacion(res, req, function (criterio) {
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
        });
    });

    app.get("/api/message/read/:id", function (req, res) {
        let criterio = {
            _id: gestorBD.mongo.ObjectID(req.params.id),
        };
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
