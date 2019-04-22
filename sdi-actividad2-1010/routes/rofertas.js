module.exports = function (app, swig, gestorBD) {

    app.get("/sale/new", function (req, res) {
        if (req.session.usuario == null) {
            res.redirect("/login");
        }
        if (req.session.usuario.rol == "rol_admin") {
            res.redirect("/homeAdmin?mensaje=No puede acceder a esa parte de la web");
        }
        var respuesta = swig.renderFile('views/newSale.html', {});
        res.send(respuesta);
    });


    app.post("/sale/new", function (req, res) {

        if (req.session.usuario == null) {
            res.redirect("/login");
        }

        if (req.title === null || req.title === "") {
            res.redirect("/sale/new?mensaje=El título no puede estar vacio");
        }
        if (req.description === null || req.description === "") {
            res.redirect("/sale/new?mensaje=La descripción no puede estar vacio");
        }
        if (req.price === null || req.price === "") {
            res.redirect("/sale/new?mensaje=El precio no puede estar vacio");

        }

        if (req.price <= 0) {
            res.redirect("/sale/new?mensaje=El precio debe ser mayor que 0€");
        }

        var oferta = {
            title: req.title,
            description: req.description,
            price: req.price,
            valid: true,
            owner: req.session.usuario,
            buyer: null
        }

        gestorBD.insertarOferta(oferta, function (id) {
            if (id == null) {
                //res.send("error al insertar")
                res.redirect("/sale/new?mensaje=Error al publicar la oferta");
            } else {
                //res.redirect("/identificarse");
                res.redirect("/sale/new?mensaje=La oferta se publicó correctamente");
            }
        });
    })

};


