// Módulos

let express = require("express");
let app = express();

let rest = require('request');
app.set('rest', rest);


app.use(function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Credentials", "true");
    res.header("Access-Control-Allow-Methods", "POST, GET, DELETE, UPDATE, PUT");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
    // Debemos especificar todas las headers que se aceptan. Content-Type , token
    next();
});

let jwt = require('jsonwebtoken');
app.set('jwt', jwt);

let expressSession = require('express-session');
app.use(expressSession({
    secret: 'abcdefg',
    resave: true,
    saveUninitialized: true

}));

let crypto = require('crypto');

let fileUpload = require('express-fileupload');
app.use(fileUpload());
let swig = require('swig-templates');
let mongo = require('mongodb');

let bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

// routerUsuarioEstandar
var routerUsuarioEstandar = express.Router();
routerUsuarioEstandar.use(function (req, res, next) {
    if (req.session.usuario !== undefined) {
        if (req.session.usuario.rol === "rol_estandar") {
            res.redirect("/home?mensaje=No puede acceder a esa parte de la web")
        } else {
            next();
        }
    } else {
        res.redirect("/login");
    }
});
app.use("/homeAdmin", routerUsuarioEstandar);
app.use("/user/list", routerUsuarioEstandar);
app.use("/user/delete", routerUsuarioEstandar);


// routerUsuarioEstandar
var routerUsuarioAdmin = express.Router();
routerUsuarioAdmin.use(function (req, res, next) {
    console.log("routerUsuarioSession");
    if (req.session.usuario !== undefined) {
        if (req.session.usuario.rol === "rol_admin") {
            res.redirect("/homeAdmin?mensaje=No puede acceder a esa parte de la web")
        } else {
            next();
        }
    } else {
        res.redirect("/login");
    }
});

app.use("/sale/all", routerUsuarioAdmin);
app.use("/sale/bought", routerUsuarioAdmin);
app.use("/sale/own", routerUsuarioAdmin);
app.use("/home", routerUsuarioAdmin);
app.use("/sale/buy/:id", routerUsuarioAdmin);
app.use("/sale/delete/:id", routerUsuarioAdmin);
app.use("/sale/new", routerUsuarioAdmin);
app.use("/sale/search",routerUsuarioAdmin);


// routerUsuarioToken
let routerUsuarioToken = express.Router();
routerUsuarioToken.use(function (req, res, next) {
    // obtener el token, vía headers (opcionalmente GET y/o POST).
    let token = req.headers['token'] || req.body.token || req.query.token;
    if (token != null) {
        // verificar el token
        jwt.verify(token, 'secreto', function (err, infoToken) {
            if (err || (Date.now() / 1000 - infoToken.tiempo) > 24000) {
                res.status(403); // Forbidden
                res.json({
                    acceso: false,
                    error: 'Token invalido o caducado'
                });
                // También podríamos comprobar que intoToken.usuario existe
            } else {
                // dejamos correr la petición
                res.usuario = infoToken.usuario;
                next();
            }
        });

    } else {
        res.status(403); // Forbidden
        res.json({
            acceso: false,
            mensaje: 'No hay Token'
        });
    }
});
// Aplicar routerUsuarioToken

app.use("/api/message/delete/:id",routerUsuarioToken);
app.use("/api/message/read/:id",routerUsuarioToken);

let gestorBD = require("./modules/gestorBD.js");
gestorBD.init(app, mongo);

app.use(express.static('public'));


//Variables

app.set('port', 8081);
app.set('db', 'mongodb://admin:admin@sdi-actividad2-1010-shard-00-00-2djmo.mongodb.net:27017,sdi-actividad2-1010-shard-00-01-2djmo.mongodb.net:27017,sdi-actividad2-1010-shard-00-02-2djmo.mongodb.net:27017/test?ssl=true&replicaSet=sdi-actividad2-1010-shard-0&authSource=admin&retryWrites=true');
app.set('clave', 'abcdefg');
app.set('crypto', crypto);
//Rutas/controladores por lógica

require("./routes/rusuarios.js")(app, swig, gestorBD); // (app,param1, param2, etc)
require("./routes/rofertas.js")(app, swig, gestorBD); // (app,param1, param2, etc)
require("./routes/rapiusuarios.js")(app, gestorBD);
require("./routes/rapiofertas.js")(app, gestorBD);
require("./routes/rapimensajes.js")(app, gestorBD);

app.use(function (err, req, res, next) {
    console.log("Error producido: " + err); //we log the error in our db
    if (!res.headersSent) {
        res.status(400);
        res.redirect("/login");
    }


});

app.get("/", function (req, res) {
    if (req.session.usuario !== undefined) {
        if (req.session.usuario.rol === "rol_admin") {
            res.redirect("/homeAdmin")
        } else {
            res.redirect("/home")
        }
    } else {
        res.redirect("/login");
    }
});
//Lanzar el servidor

app.listen(app.get('port'), function () {
    console.log("Servidor activo");
});