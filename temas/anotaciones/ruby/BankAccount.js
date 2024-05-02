"use strict";
// BankAccount.ts
var __esDecorate = (this && this.__esDecorate) || function (ctor, descriptorIn, decorators, contextIn, initializers, extraInitializers) {
    function accept(f) { if (f !== void 0 && typeof f !== "function") throw new TypeError("Function expected"); return f; }
    var kind = contextIn.kind, key = kind === "getter" ? "get" : kind === "setter" ? "set" : "value";
    var target = !descriptorIn && ctor ? contextIn["static"] ? ctor : ctor.prototype : null;
    var descriptor = descriptorIn || (target ? Object.getOwnPropertyDescriptor(target, contextIn.name) : {});
    var _, done = false;
    for (var i = decorators.length - 1; i >= 0; i--) {
        var context = {};
        for (var p in contextIn) context[p] = p === "access" ? {} : contextIn[p];
        for (var p in contextIn.access) context.access[p] = contextIn.access[p];
        context.addInitializer = function (f) { if (done) throw new TypeError("Cannot add initializers after decoration has completed"); extraInitializers.push(accept(f || null)); };
        var result = (0, decorators[i])(kind === "accessor" ? { get: descriptor.get, set: descriptor.set } : descriptor[key], context);
        if (kind === "accessor") {
            if (result === void 0) continue;
            if (result === null || typeof result !== "object") throw new TypeError("Object expected");
            if (_ = accept(result.get)) descriptor.get = _;
            if (_ = accept(result.set)) descriptor.set = _;
            if (_ = accept(result.init)) initializers.unshift(_);
        }
        else if (_ = accept(result)) {
            if (kind === "field") initializers.unshift(_);
            else descriptor[key] = _;
        }
    }
    if (target) Object.defineProperty(target, contextIn.name, descriptor);
    done = true;
};
var __runInitializers = (this && this.__runInitializers) || function (thisArg, initializers, value) {
    var useValue = arguments.length > 2;
    for (var i = 0; i < initializers.length; i++) {
        value = useValue ? initializers[i].call(thisArg, value) : initializers[i].call(thisArg);
    }
    return useValue ? value : void 0;
};
var __setFunctionName = (this && this.__setFunctionName) || function (f, name, prefix) {
    if (typeof name === "symbol") name = name.description ? "[".concat(name.description, "]") : "";
    return Object.defineProperty(f, "name", { configurable: true, value: prefix ? "".concat(prefix, " ", name) : name });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.BankAccount = void 0;
// Decorador -> @Comparable
function Comparable(value, context) {
    value.prototype.compareTo = function (value) {
        if (this == value)
            return 0; // evitar autocomparación
        if (!(value instanceof BankAccount))
            throw new Error("No se puede comparar con un objeto que no sea BankAccount");
        return this.getId().localeCompare(value.getId()); // comparación por id
    };
    value.prototype.equals = function (value) {
        if (this == value)
            return true; // evitar autocomparación
        if (!(value instanceof BankAccount))
            return false;
        return this.getId() == value.getId(); // comparación por id
    };
    value.prototype.toString = function () {
        return this.getId();
    };
}
var BankAccount = function () {
    var _classDecorators = [Comparable];
    var _classDescriptor;
    var _classExtraInitializers = [];
    var _classThis;
    var BankAccount = _classThis = /** @class */ (function () {
        function BankAccount_1(id) {
            this.id = id;
        }
        BankAccount_1.prototype.getCreationDate = function () {
            return this.creationDate;
        };
        BankAccount_1.prototype.setCreationDate = function (creationDate) {
            this.creationDate = creationDate;
        };
        BankAccount_1.prototype.getId = function () {
            return this.id;
        };
        return BankAccount_1;
    }());
    __setFunctionName(_classThis, "BankAccount");
    (function () {
        var _metadata = typeof Symbol === "function" && Symbol.metadata ? Object.create(null) : void 0;
        __esDecorate(null, _classDescriptor = { value: _classThis }, _classDecorators, { kind: "class", name: _classThis.name, metadata: _metadata }, null, _classExtraInitializers);
        BankAccount = _classThis = _classDescriptor.value;
        if (_metadata) Object.defineProperty(_classThis, Symbol.metadata, { enumerable: true, configurable: true, writable: true, value: _metadata });
        __runInitializers(_classThis, _classExtraInitializers);
    })();
    return BankAccount = _classThis;
}();
exports.BankAccount = BankAccount;
