import { PedidoService, PedidoError } from './../src/pedidoService';
import { Almacen, Articulo, ArticuloError } from '../src/almacen';

describe('PedidoService', () => {
    let pedidoService: PedidoService;
    let almacen: Almacen;

    beforeEach(() => {
        almacen = new Almacen();
        pedidoService = new PedidoService(almacen);
    });

    test('realizarPedido lanza un error si el pedido está vacío', async () => {
        // Pedido vacío
        const pedidoVacio: Articulo[] = [];

        // Debe lanzar un error de PedidoError.PedidoVacio
        await expect(pedidoService.realizarPedido(pedidoVacio)).rejects.toThrow(PedidoError.PedidoVacio());
    });

    test('realizarPedido actualiza el stock de los articulos y devuelve undefined si el pedido se realiza con éxito', async () => {
        // Configuración de los articulos en el restaurante
        const articulo1: Articulo = { nombre: 'articulo1', cantidad: 10 };
        const articulo2: Articulo = { nombre: 'articulo2', cantidad: 5 };
        almacen.insertarArticulo(articulo1);
        almacen.insertarArticulo(articulo2);

        // Pedido exitoso
        const pedidoExitoso: Articulo[] = [
            { nombre: 'articulo1', cantidad: 2 },
            { nombre: 'articulo2', cantidad: 3 }
        ];

        await expect(pedidoService.realizarPedido(pedidoExitoso)).resolves.toBeUndefined();

        // Comprobación de que el stock se ha actualizado correctamente
        expect(almacen.obtenerArticulosDisponibles()).toEqual([
            { nombre: 'articulo1', cantidad: 8 },
            { nombre: 'articulo2', cantidad: 2 }
        ]);
    });

    test('realizarPedido lanza un error si el articulo no existe en el restaurante', async () => {
        // Pedido con articulo inexistente
        const pedidoarticuloInexistente: Articulo[] = [
            { nombre: 'articuloInexistente', cantidad: 2 }
        ];

        // Debe lanzar un error de articuloError.articuloNoEncontrado
        await expect(pedidoService.realizarPedido(pedidoarticuloInexistente)).rejects.toThrow(ArticuloError.ArticuloNoEncontrado('articuloInexistente'));
    });

    test('realizarPedido lanza un error si no hay suficiente stock para el pedido', async () => {
        // Configuración de los articulos en el restaurante
        const articulo: Articulo = { nombre: 'articulo3', cantidad: 2 };
        almacen.insertarArticulo(articulo);

        // Pedido con cantidad mayor que el stock disponible
        const pedidoCantidadInsuficiente: Articulo[] = [
            { nombre: 'articulo3', cantidad: 3 }
        ];

        await expect(pedidoService.realizarPedido(pedidoCantidadInsuficiente)).rejects.toThrow(ArticuloError.ArticuloStockInsuficiente('articulo3'));
    });
});
