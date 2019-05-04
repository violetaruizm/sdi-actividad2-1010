module.exports = function (app, swig, gestorBD) {
    app.get("/admin", function (req, res) {
        let respuesta = swig.renderFile('views/admin.html', {
            user: req.session.user
        });
        app.get("logger").info('Admin se ha dirijido a la pagina de administracion principal');
        res.send(respuesta);
    });

    app.get("/resetdb", function (req, res) {
        gestorBD.resetDB(function (result) {
            if (result == null) {
                res.redirect("/identificarse?mensaje=Error al resetear las colecciones&tipoMensaje=alert-danger");
            } else {
                app.get("logger").info('Admin ha reseteado la base de datos');
                addAdmin(res);
            }
        });
    });

    function addAdmin(res) {
        let seguro = app.get("crypto").createHmac('sha256',
            app.get('clave')).update('admin').digest('hex');
        let cri = {email: 'admin@email.com'};
        gestorBD.obtenerUsuarios(cri, function (usuarios) {
            if (usuarios != null && usuarios.length !== 0) {
                res.redirect("/login?mensaje=Admin ya existe" + "&tipoMensaje=alert-danger ");
            } else {
                let admin = {
                    name: 'admin',
                    surname: 'admin',
                    email: 'admin@email.com',
                    password: seguro,
                    rol: 'rol_admin',
                    valid: true
                };
                gestorBD.insertarUsuario(admin, function (id) {
                    if (id == null) {
                        app.get("logger").error('Error registro de admin');
                        res.redirect("/identificarse?mensaje=Error al insertar admin en reset&tipoMensaje=alert-danger");
                    } else {
                        app.get("logger").info('Usuario admin creado');
                        createData(res);
                    }
                })
            }
        })
    }

    function createData(res) {
        const listUsers = [];
        const limitUsers = 5;
        const numOffersUser = 3;
        const numMessageUser = 2;
        const letters = ['a', 'b', 'c'];

        for (let i = 1; i <= limitUsers; i++) {
            let password = app.get("crypto").createHmac('sha256',
                app.get('clave')).update('123456789').digest('hex');
            let user = {
                name: 'User' + i,
                surname: 'User' + i,
                email: 'user' + i + '@email.com',
                password: password,
                rol: 'rol_estandar',
                money: 100.0,
                valid: true
            };
            listUsers.push(user);
        }
        gestorBD.insertDataTest(listUsers, 'usuarios', function (users) {
            if (users == null || users.length === 0) {
                res.redirect("/login?mensaje=Error al crear los usuarios de prueba");
            } else {
                const listOffers = [];
                let j = 1;
                listUsers.forEach(user => {

                    for (let i = 1; i <= numOffersUser; i++) {
                        let offer = {
                            title: 'Oferta ' + j + letters[i-1],
                            description: 'Oferta ' + j + letters[i-1],
                            price: i * 5,
                            owner: user.email,
                            onsale: true,
                            buyer: null,
                            valid: true,
                            date: new Date()

                        };
                        listOffers.push(offer);
                    }
                    j++;
                });
                listOffers[4].price=150;
                listOffers[5].price=100;
                gestorBD.insertDataTest(listOffers, 'ofertas', function (offers) {
                    if (offers == null || offers.length === 0) {
                        res.redirect("/login?mensaje=Error al crear las ofertas de prueba");
                    } else {
                        const listConversaciones = [];
                        listOffers.forEach(offer => {
                            listUsers.forEach(user => {
                                if (user.email !== offer.owner) {
                                    for (let i = 1; i <= numMessageUser; i++) {
                                        let conversacion = {
                                            user1: user.email,
                                            user2: offer.owner,
                                            sale: offer._id,
                                            valid: true

                                        };
                                        listConversaciones.push(conversacion);
                                    }
                                }
                            });
                        });
                        gestorBD.insertDataTest(listConversaciones, 'conversaciones', function (conversaciones) {
                            if (conversaciones == null || conversaciones.length === 0) {
                                res.redirect("/login?mensaje=Error al crear las ofertas de prueba");
                            } else {

                                const listMensajes = [];

                                listConversaciones.forEach(conver => {

                                    for (let i = 1; i <= numMessageUser; i++) {
                                        let mensaje = {
                                            sale: conver.sale,
                                            idConver: conver._id,
                                            sender: conver.user1,
                                            receiver: conver.user2,
                                            valid: true,
                                            read: false,
                                            message: "abcdefg",
                                            date: new Date()

                                        };
                                        listMensajes.push(mensaje);
                                    }

                                });
                                gestorBD.insertDataTest(listMensajes, 'mensajes', function (mensajes) {
                                    if (mensajes === null || mensajes.length === 0) {
                                        res.redirect("/login?mensaje=Error al crear las ofertas de prueba");

                                    } else {
                                        res.redirect("/login?mensaje=Datos creado correctamente");
                                    }
                                })


                            }
                        });
                    }
                });
            }
        });
    }
}
;