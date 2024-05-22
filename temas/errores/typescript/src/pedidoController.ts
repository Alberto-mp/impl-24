import express from 'express';
import bodyParser from 'body-parser';
import { PedidoService } from './pedidoService'; // Asegúrate de que la ruta sea correcta
import { Almacen, Articulo } from './almacen'; // Asegúrate de que la ruta sea correcta

const app = express();

app.use(express.static('web'));

app.get('/', function (req, res) {
    res.sendFile('index.html', { root: '../web' });
});


// Ejemplo de articulos
const almacen = new Almacen();
almacen.insertarArticulo({ nombre: 'Tornillo', cantidad: 100 });
almacen.insertarArticulo({ nombre: 'Tuerca', cantidad: 80 });
almacen.insertarArticulo({ nombre: 'Sierra', cantidad: 15 });
almacen.insertarArticulo({ nombre: 'Cerrojo', cantidad: 13 });

const pedidoService = new PedidoService(almacen);

app.use(bodyParser.json());

app.post('/realizarPedido', async (req, res) => {
    try {
        await pedidoService.realizarPedido(req.body);
        res.status(200).send('Pedido realizado con éxito');
    } catch (error) {
        if (error instanceof Error) res.status(400).send(error.message);
    }
});

app.listen(3300, () => {
    console.log('Servidor escuchando en el puerto 3300');
});