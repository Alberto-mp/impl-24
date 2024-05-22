import { Almacen, Articulo, ArticuloError } from './almacen';

// Error para los pedidos
export class PedidoError extends Error {
    private constructor(message: string) {
        super(message);
        this.name = 'PedidoError';
    }

    public static PedidoVacio(): PedidoError {
        return new PedidoError('El pedido no puede estar vacío.');
    }
}

export class PedidoService {
    private almacen: Almacen;

    constructor(almacen: Almacen) {
        this.almacen = almacen;
    }

    public async realizarPedido(pedido: Articulo[]): Promise<void> {
        // Comprueba si el pedido está vacío
        if (pedido === undefined || pedido.length === 0) throw PedidoError.PedidoVacio();

        // Verifica la disponibilidad de cada artículo antes de realizar el pedido
        pedido.forEach(articuloPedido => {
            this.almacen.verificarDisponibilidadArticulo(articuloPedido.nombre, articuloPedido.cantidad);
        });

        // Actualiza el stock de los artículos
        pedido.forEach(articuloPedido => {
            this.almacen.actualizarStock(articuloPedido);
        });

        return undefined;
    }
}