function mostrarModalNuevo() {
    const modal = new bootstrap.Modal(document.getElementById('modalNuevo'));
    modal.show();
}

function confirmarEliminar(idServicio) {
    document.getElementById('idEliminar').value = idServicio;
    const modal = new bootstrap.Modal(document.getElementById('modalEliminar'));
    modal.show();
}

function ejecutarEliminar() {
    document.getElementById('formEliminar').submit();
}

function confirmarCancelar(idCita) {
    document.getElementById('idCancelar').value = idCita;
    const modal = new bootstrap.Modal(document.getElementById('modalCancelar'));
    modal.show();
}

function ejecutarCancelar() {
    document.getElementById('formCancelar').submit();
}