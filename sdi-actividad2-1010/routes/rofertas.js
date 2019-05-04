module.exports = function (app, swig, gestorBD) {

    app.get("/sale/new", function (req, res) {

        var respuesta = swig.renderFile('views/newSale.html', { dineroUsuario:req.session.usuario.money});
        res.send(respuesta);
    });


    app.post("/sale/new", function (req, res) {


        console.log(req.body.title);
        if (req.body.title === null || req.body.title === "") {
            app.get('logger').error('Nueva oferta: El título no puede estar vacio');
            res.redirect("/sale/new?mensaje=El título no puede estar vacio");

        }
        if (req.body.description === null || req.body.description === "") {
            app.get('logger').error('Nueva oferta: La descripción no puede estar vacio');
            res.redirect("/sale/new?mensaje=La descripción no puede estar vacio");
        }
        if (req.body.price === null || req.body.price === "") {
            app.get('logger').error('Nueva oferta: El precio no puede estar vacio');
            res.redirect("/sale/new?mensaje=El precio no puede estar vacio");

        }

        if (typeof (parseInt(req.body.price)) != "number") {
            app.get('logger').error('Nueva oferta:Introduzca un precio correcto');

            res.redirect("/sale/new?mensaje=Introduzca un precio correcto");
        }

        var price = parseInt(req.body.price);

        if (price <= 0) {
            app.get('logger').error('Nueva oferta:El precio debe ser mayor que 0€');
            res.redirect("/sale/new?mensaje=El precio debe ser mayor que 0€");
        }
        var datePost = new Date();

        var oferta = {
            title: req.body.title,
            description: req.body.description,
            price: price,
            valid: true,
            owner: req.session.usuario.email,
            buyer: null,
            date: datePost,
            onsale: true
        }

        gestorBD.insertarOferta(oferta, function (id) {
            if (id == null) {
                //res.send("error al insertar")
                app.get('logger').error('Nueva oferta:Error al publicar la oferta');
                res.redirect("/sale/new?mensaje=Error al publicar la oferta");
            } else {
                //res.redirect("/identificarse");
                app.get('logger').info('Nueva oferta:La oferta se publicó correctamente');
                res.redirect("/sale/new?mensaje=La oferta se publicó correctamente");
            }
        });
    });

    app.get("/sale/own", function (req, res) {

        var criterio = {owner: req.session.usuario.email};
        gestorBD.obtenerOfertas
        (criterio, function (ofertas) {
            console.log(ofertas)
            var respuesta = swig.renderFile('views/postedSales.html', {salesList: ofertas, dineroUsuario:req.session.usuario.money});
            app.get('logger').info('Ofertas propias:Se van a mostrar las ofertas');
            res.send(respuesta);
        });


    });

    app.get("/sale/delete/:id", function (req, res) {

        var criterioNuevo = {valid: false};
        var criterio = {_id: gestorBD.mongo.ObjectID(req.params.id)};

        gestorBD.deleteSale(criterio, criterioNuevo, function (ofertas) {


            if (ofertas == null || ofertas.length == 0) {
                app.get('logger').error('Eliminar oferta:La oferta no pudo eliminarse correctamente');
                res.redirect("/sale/own" +
                    "?mensaje=La oferta no pudo eliminarse correctamente");
            } else {
                app.get('logger').info('Eliminar oferta:La oferta se eliminó correctamente');
                res.redirect("/sale/own" +
                    "?mensaje=La oferta se eliminó correctamente");
            }
        });

    });

    app.get("/sale/bought", function (req, res) {


        var criterio = {
            buyer: req.session.usuario.email,
            valid: true
        };
        gestorBD.obtenerOfertas
        (criterio, function (ofertas) {
            console.log(ofertas)
            var respuesta = swig.renderFile('views/boughtSales.html', {salesList: ofertas, dineroUsuario:req.session.usuario.money});
            app.get('logger').info('Comprar oferta:La oferta se compró correctamente');
            res.send(respuesta);
        });

    });

    app.get("/sale/all", function (req, res) {

        gestorBD.obtenerOfertas({
            valid: true,
            onsale: true,
            owner: {
                $ne: req.session.usuario.email
            }
        }, function (ofertas) {
            var respuesta = swig.renderFile('views/allSales.html', {
                salesList: ofertas,
                userEmail: req.session.usuario.email,
                dineroUsuario:req.session.usuario.money
            });
            app.get('logger').info('Todas las ofertas:se van a mostrar todas las ofertas');
            res.send(respuesta);
        })

        ;


    });

    app.get("/sale/buy/:id", function (req, res) {
        var criterio = {_id: gestorBD.mongo.ObjectID(req.params.id)};
        gestorBD.obtenerOfertas
        (criterio, function (ofertas) {
            if (ofertas == null || ofertas.length == 0) {
                app.get('logger').error('Comprar oferta:La compra no pudo completarse');
                res.redirect("/sale/all?mensaje=La compra no pudo completarse");
            } else {

                if (ofertas[0].price <= req.session.usuario.money) {
                    var nuevoCriterio = {
                        onsale: false,
                        buyer: req.session.usuario.email
                    };
                    var criterioComprador = {
                        email: req.session.usuario.email
                    };
                    var nuevoCriterioComprador = {
                        money: req.session.usuario.money - ofertas[0].price
                    };

                    gestorBD.comprarOferta(criterio, nuevoCriterio, function (ofertas) {


                        if (ofertas == null || ofertas.length == 0) {
                            app.get('logger').error('Comprar oferta:La oferta no pudo comprarse correctamente');
                            res.redirect("/sale/all" +
                                "?mensaje=La oferta no pudo comprarse correctamente");
                        } else {

                            gestorBD.actualizarDinero(criterioComprador, nuevoCriterioComprador, function (usuarios) {
                                if (usuarios == null || usuarios.length == 0) {
                                    app.get('logger').error('Comprar oferta:La oferta no pudo comprarse correctamente');
                                    res.redirect("/sale/all" +
                                        "?mensaje=La oferta no pudo comprarse correctamente");

                                } else {
                                    app.get('logger').info('Comprar oferta:La oferta se compró correctamente');
                                    req.session.usuario.money=nuevoCriterioComprador.money;
                                    res.redirect("/sale/bought" +
                                        "?mensaje=La oferta se compró correctamente");

                                }
                            })


                        }
                    });


                } else {
                    app.get('logger').error('Comprar oferta:No tienes suficiente dinero para adquirir esa oferta');
                    res.redirect("/sale/all?mensaje=No tienes suficiente dinero para adquirir esa oferta");
                }
            }
        })


    });

    app.get("/sale/search", function (req, res) {
        var criterio = {
            valid: true,
            onsale: true,
            owner: {
                $ne: req.session.usuario.email
            }
        };
        if (req.query.busqueda != null && req.query.busqueda !== "") {
            criterio = {
                $and: [{
                    $or: [
                        {
                            description: {
                                $regex: ".*" + req.query.busqueda + ".*", $options: 'i'
                            }
                        },
                        {
                            title: {
                                $regex: ".*" + req.query.busqueda + ".*", $options: 'i'
                            }
                        }
                    ],
                    valid: true,
                    onsale: true,
                    owner: {
                        $ne: req.session.usuario.email
                    }
                }]
            };
        }
        var pg = parseInt(req.query.pg); // Es String !!!
        if (req.query.pg == null) { // Puede no venir el param
            pg = 1;
        }

        gestorBD.obtenerOfertasPg(criterio, pg, function (ofertas, total) {
            if (ofertas == null) {
                app.get('logger').error('Buscar oferta:Error al listar');
                res.send("Error al listar ");
            } else {
                let ultimaPg = total / 5;
                if (total % 5 > 0) { // Sobran decimales
                    ultimaPg = ultimaPg + 1;
                }
                let paginas = []; // paginas mostrar
                for (let i = pg - 2; i <= pg + 2; i++) {
                    if (i > 0 && i <= ultimaPg) {
                        paginas.push(i);
                    }
                }
                console.log(total);
                var respuesta = swig.renderFile('views/pagination.html',
                    {

                        busqueda: req.query.busqueda,
                        salesList: ofertas,
                        userEmail: req.session.usuario.email,
                        paginas: paginas,
                        ultimaPg: ultimaPg,
                        actual: pg,
                        dineroUsuario:req.session.usuario.money
                    });
                app.get('logger').info('Buscar oferta:se van a mostrar las ofertas que coinciden con la búsqueda');
                res.send(respuesta);
            }
        });
    });


};


