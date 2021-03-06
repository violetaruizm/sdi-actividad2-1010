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
                    app.get('logger').error('API enviar mensaje: no se puede enviar un mensaje a esa oferta');
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
                            let converNueva = {
                                sale: gestorBD.mongo.ObjectID(req.body.idSale),
                                user1: req.body.receiver,
                                user2: usuario,
                                valid: true
                            }
                            gestorBD.crearConversacion(converNueva, function (nuevaConversacion) {
                                if (nuevaConversacion === null) {
                                    res.status(204); // Unauthorized
                                    app.get('logger').error('API enviar mensaje: no se pudo crear la conversación');
                                    res.json({
                                        error: "No se pudo crear la conversación"
                                    });

                                } else {
                                    let mensaje = {
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
                                            app.get('logger').error('API enviar mensaje: no se pudo enviar el mensaje');
                                            res.json({
                                                error: "No se pudo enviar el mensaje"
                                            });
                                        } else {
                                            res.status(200);
                                            app.get('logger').info('API enviar mensaje: se envio correctamente el mensaje');
                                            res.send(JSON.stringify(mensaje));
                                        }
                                    });

                                }
                            })


                        } else {

                            // si el recibidor es nulo es que el que está enviando el mensaje es
                            // el propietario de la oferta
                            let mensaje = {
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
                                    app.get('logger').error('API enviar mensaje: no se pudo enviar el mensaje');
                                    res.json({
                                        error: "No se pudo enviar el mensaje"
                                    });
                                } else {
                                    res.status(200);
                                    app.get('logger').info('API enviar mensaje: se envio correctamente el mensaje');
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
                app.get('logger').error('API obtener conversación: la oferta no existe');
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
                    app.get('logger').info('API obtener conversación: la conversación está vacía');
                    res.send(JSON.stringify([]));
                } else {
                    let criterio1 = {
                        idConver: conversaciones[0]._id

                    };
                    gestorBD.obtenerMensajesConversacion(criterio1, function (mensajesConver) {
                        if (mensajesConver === null || mensajesConver.length === 0) {
                            res.status(204); // Unauthorized
                            app.get('logger').error('API obtener conversación: No se encontraron los mensajes');
                            res.json({
                                error: "No se encontraron los mensajes"
                            });

                        } else {
                            app.get('logger').info('API obtener conversación: se obtuvo correctamente la conversación ');
                            res.send(JSON.stringify(mensajesConver));
                        }
                    })

                }
            })
        });
    });

    app.get("/api/conversation/delete/:idConversacion", function (req, res) {
        let criterio = {
            idConver: gestorBD.mongo.ObjectID(req.params.idConversacion)
        };
        gestorBD.deleteMensajesConversacion(criterio, function (mensajes) {
            if (mensajes === null) {
                res.status(204); // Unauthorized
                app.get('logger').error('API borrar conversación: No se pudieron eliminar los mensajes');
                res.json({
                    error: "No se pudieron eliminar los mensajes"
                });
            } else {

                gestorBD.deleteConversacion({_id: gestorBD.mongo.ObjectID(req.params.idConversacion)},
                    function (conversacion) {
                        if (mensajes === null) {
                            res.status(204); // Unauthorized
                            app.get('logger').error('API borrar conversación: No se pudieron eliminar los mensajes');
                            res.json({
                                error: "No se pudieron eliminar los mensajes"
                            })
                        } else {
                            app.get('logger').error('API borrar conversación: La conversación se eliminó correctamente');
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
                app.get('logger').error('API marcar mensaje leído: no se pudo marcar como leído el mensaje');
                res.json({
                    error: "No se pudo marcar como leído el mensaje"
                })
            } else {
                res.status(200);
                app.get('logger').info('API marcar mensaje leído: el mensaje se marcó como leído');
                res.send(JSON.stringify(mensajes));
            }
        })
    });

    app.get("/api/conversations/own", function (req, res) {
        console.log(res.usuario);
        let criterio = {$or: [{user1: res.usuario}, {user2: res.usuario}]};
            gestorBD.obtenerConversacion(criterio, function (conversaciones){
                if(conversaciones===null || conversaciones.length===0){
                    app.get('logger').info('API mis conversaciones: No tengo ninguna conversación abierta');
                    res.send(JSON.stringify([]));

                }else{
                    app.get('logger').info('API mis conversaciones: Se obtuvieron todas mis conversaciones');
                    res.send(JSON.stringify(conversaciones));
                }

            }

            )
        }
    )
}
;
