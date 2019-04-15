
module.exports = function (app, swig,gestorBD) {
    app.get("/signup", function (req, res) {
        var respuesta = swig.renderFile('views/signUp.html', {});
        res.send(respuesta);
    });


    app.post('/signup', function (req, res) {

        if(req.body.email===null || req.body.email==="") {
            res.redirect("/signup?mensaje=El email no puede estar vacío");


        }



        if(req.body.password===null|| req.body.password==="" ){
            res.redirect("/signup?mensaje=La contraseña no puede estar vacía");
        }

        if(req.body.password.length>=0 && req.body.password.length<8){
            res.redirect("/signup?mensaje=La contraseña debe tener más de 8 caracteres");

        }

        if(req.body.repassword.length>=0 && req.body.repassword.length<8){
            res.redirect("/signup?mensaje=Repita la contraseña");

        }
        if(req.body.repassword!=req.body.password){
            res.redirect("/signup?mensaje=Las contraseñas no coinciden");

        }

        var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');

        var usuario = {
            email: req.body.email,
            password: seguro,
            name : req.body.name,
            surname : req.body.surname,
            money : 100,
            rol : "rol_estandar"
        }

        var criterio = {
            email: req.body.email
        }
        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios != null && usuarios.length != 0) {
                res.redirect("/signup?mensaje=El email ya está registrado. Inténtelo de nuevo con un " +
                    "email diferente");
            } else {
                gestorBD.insertarUsuario(usuario, function (id) {
                    if (id == null) {
                        //res.send("error al insertar")
                        res.redirect("/signup?mensaje=Error al registrar usuario");
                    } else {
                        //res.redirect("/identificarse");
                        res.redirect("/login?mensaje=Nuevo usuario registrado");
                    }
                })
            }
        })
    });

};