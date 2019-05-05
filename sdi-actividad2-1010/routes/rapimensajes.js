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

                        $or: [
                            {
                                $and: [
                                    {user1: usuario},
                                    {user2: ofertas[0].owner},
                                    {sale: gestorBD.mongo.ObjectID(req.body.idSale)}
                                ]
                            },
                            {
                                $and: [
                                    {user1: ofertas[0].owner},
                                    {user2: usuario},
                                    {sale: gestorBD.mongo.ObjectID(req.body.idSale)}
                                ]
                            }
                        ],

                        valid: true,

                    };

                    gestorBD.obtenerConversacion(criterio, function (conversacion) {
                        if (conversacion == null || conversacion.length === 0) {
                            var converNueva = {
                                sale: gestorBD.mongo.ObjectID(req.body.idSale),
                                user1: req.body.receiver,
                                user2: usuario,
                                valid: true
                            }
                            gestorBD.crearConversacion(converNueva, function (nuevaConversacion) {
                                if (nuevaConversacion === null) {
                                    res.status(204); // Unauthorized
                                    res.json({
                                        error: "No se pudo crear la conversación"
                                    });

                                } else {
                                    var mensaje = {
                                        sale: gestorBD.mongo.ObjectID(req.body.idSale),
                                        receiver: req.body.receiver,
                                        sender: usuario,
                                        message: req.body.message,
                                        valid: true,
                                        read: false,
                                        date: new Date(),
                                        idConver: nuevaConversacion
                                    }
                                    gestorBD.enviarMensaje(mensaje, function (id) {
                                        if (id === null) {
                                            res.status(204); // Unauthorized
                                            res.json({
                                                error: "No se pudo enviar el mensaje"
                                            });
                                        } else {
                                            res.status(200);
                                            res.send(JSON.stringify(mensaje));
                                        }
                                    });

                                }
                            })


                        } else {

                            // si el recibidor es nulo es que el que está enviando el mensaje es
                            // el propietario de la oferta
                            var mensaje = {
                                sale: gestorBD.mongo.ObjectID(req.body.idSale),
                                receiver: req.body.receiver,
                                sender: usuario,
                                message: req.body.message,
                                valid: true,
                                read: false,
                                date: new Date(),
                                idConver: conversacion[0]._id
                            }
                            gestorBD.enviarMensaje(mensaje, function (id) {
                                if (id === null) {
                                    res.status(204); // Unauthorized
                                    res.json({
                                        error: "No se pudo enviar el mensaje"
                                    });
                                } else {
                                    res.status(200);
                                    res.send(JSON.stringify(mensaje));
                                }
                            });

                        }
                    })

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
                let usuario1 = req.body.usuario1;
                let usuario2 = res.usuario;
                //el usuario no es el dueño de la oferta

                criterio = {
                    $or: [
                        {
                            $and: [
                                {user1: usuario1},
                                {user2: usuario2},
                                {sale: gestorBD.mongo.ObjectID(req.params.idSale)}
                            ]
                        },
                        {
                            $and: [
                                {user1: usuario2},
                                {user2: usuario1},
                                {sale: gestorBD.mongo.ObjectID(req.params.idSale)}
                            ]
                        }
                    ]
                }

                functionCallBack(criterio);
            }
        })
    }


    // ver la conversación para el usuario que no es dueño de la oferta
    app.post("/api/conversation/:idSale", function (req, res) {
        getMensajesConversacion(res, req, function (criterio) {
            gestorBD.obtenerConversacion(criterio, function (conversaciones) {
                if (conversaciones === null || conversaciones.length === 0) {
                    // res.status(204); // Unauthorized
                    // res.json({
                    //     error: "No se pudo abrir la conversación"
                    // });

                    res.send(JSON.stringify([]));
                } else {
                    let criterio1 = {
                        idConver: conversaciones[0]._id

                    };
                    gestorBD.obtenerMensajesConversacion(criterio1, function (mensajesConver) {
                        if (mensajesConver === null || mensajesConver.length === 0) {
                            res.status(204); // Unauthorized
                            res.json({
                                error: "No se encontraron los mensajes"
                            });

                        } else {
                            res.send(JSON.stringify(mensajesConver));
                        }
                    })

                }
            })
        });
    });

    app.get("/api/conversation/delete/:idConversacion", function (req, res) {

        var criterio = {
            idConver: gestorBD.mongo.ObjectID(req.params.idConversacion)
        }
        gestorBD.deleteMensajesConversacion(criterio, function (mensajes) {
            if (mensajes === null) {
                res.status(204); // Unauthorized
                res.json({
                    error: "No se pudieron eliminar los mensajes"
                });
            } else {

                gestorBD.deleteConversacion({_id: gestorBD.mongo.ObjectID(req.params.idConversacion)},
                    function (conversacion) {
                        if (mensajes === null) {
                            res.status(204); // Unauthorized
                            res.json({
                                error: "No se pudieron eliminar los mensajes"
                            })
                        } else {
                            res.send(JSON.stringify(mensajes));
                        }
                    });
            }
        })


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

    app.get("/api/conversations/own", function (req, res) {
        console.log(res.usuario);
        let criterio = {$or: [{user1: res.usuario}, {user2: res.usuario}]};
            gestorBD.obtenerConversacion(criterio, function (conversaciones){
                if(conversaciones===null || conversaciones.length===0){
                    res.send(JSON.stringify([]));

                }else{
                    res.send(JSON.stringify(conversaciones));
                }

            }

            )
        }
    )
}
;
