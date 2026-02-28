/**
 * Theme Management Utility
 * Handles system preference detection and localStorage persistence
 */

console.log('Theme.js loaded successfully!');

class ThemeManager {
    constructor() {
        this.THEME_KEY = 'theme-preference';
        this.LIGHT = 'light';
        this.DARK = 'dark';
        this.init();
    }

    /**
     * Initialize theme on page load
     */
    init() {
        console.log('hello world');
        const savedTheme = this.getSavedTheme();
        const systemTheme = this.getSystemTheme();
        const theme = savedTheme || systemTheme;

        this.applyTheme(theme);
        this.watchSystemTheme();
    }

    /**
     * Get theme saved in localStorage
     */
    getSavedTheme() {
        try {
            return localStorage.getItem(this.THEME_KEY);
        } catch (e) {
            console.warn('localStorage not available:', e);
            return null;
        }
    }

    /**
     * Get system preference for dark mode
     */
    getSystemTheme() {
        if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
            return this.DARK;
        }
        return this.LIGHT;
    }

    /**
     * Apply theme by setting data-theme attribute on html element
     */
    applyTheme(theme) {
        const validTheme = [this.LIGHT, this.DARK].includes(theme) ? theme : this.LIGHT;
        document.documentElement.setAttribute('data-theme', validTheme);

        try {
            localStorage.setItem(this.THEME_KEY, validTheme);
        } catch (e) {
            console.warn('Could not save theme preference:', e);
        }
    }

    /**
     * Toggle between light and dark theme
     */
    toggle() {
        const currentTheme = document.documentElement.getAttribute('data-theme') || this.getSystemTheme();
        const newTheme = currentTheme === this.DARK ? this.LIGHT : this.DARK;
        this.applyTheme(newTheme);
        return newTheme;
    }

    /**
     * Get current theme
     */
    getCurrentTheme() {
        return document.documentElement.getAttribute('data-theme') || this.getSystemTheme();
    }

    /**
     * Watch for system theme changes
     */
    watchSystemTheme() {
        if (!window.matchMedia) return;

        const darkModeQuery = window.matchMedia('(prefers-color-scheme: dark)');

        // Listen for changes in system preference (modern browsers)
        darkModeQuery.addEventListener('change', (e) => {
            const savedTheme = this.getSavedTheme();
            // Only apply system theme if user hasn't manually set a preference
            if (!savedTheme) {
                this.applyTheme(e.matches ? this.DARK : this.LIGHT);
            }
        });
    }
}

// Initialize theme manager when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
        window.themeManager = new ThemeManager();
    });
} else {
    window.themeManager = new ThemeManager();
}
