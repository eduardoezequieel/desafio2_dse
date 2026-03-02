function authFetch(url, options = {}) {
    return fetch(url, options)
        .then(response => {
            if (response.status === 401) {
                localStorage.removeItem('user');
                window.location.href = '/login?redirect=/dashboard';
                return Promise.reject('Sesión expirada');
            }
            if (response.status === 403) {
                window.location.href = '/403';
                return Promise.reject('Acceso denegado');
            }
            return response;
        });
}

function logout() {
    fetch('/auth/logout', { method: 'POST' })
        .finally(() => {
            localStorage.removeItem('user');
            window.location.href = '/login';
        });
}

function toggleTheme() {
    if (window.themeManager) {
        window.themeManager.toggle();
    }
}
