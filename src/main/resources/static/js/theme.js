/**
 * Theme Management Utility
 * Handles system preference detection and localStorage persistence
 *
 * Logic:
 * - App always starts with system preference (never saves on init)
 * - Only localStorage if user manually toggles the theme
 * - System changes are ALWAYS applied (unless user previously toggled manually)
 */

class ThemeManager {
    constructor() {
        this.THEME_KEY = 'theme-preference';
        this.LIGHT = 'light';
        this.DARK = 'dark';
        this.isManualOverride = false;
        this.init();
    }

    /**
     * Initialize theme on page load
     */
    init() {
        const savedTheme = this.getSavedTheme();
        const systemTheme = this.getSystemTheme();

        // Determine if there's a manual override
        this.isManualOverride = !!savedTheme;

        // Use saved theme if exists (user manually toggled), otherwise use system
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
            console.warn('[Theme] localStorage not available:', e);
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
     * @param {string} theme - 'light' or 'dark'
     * @param {boolean} saveToStorage - whether to save this as manual override to localStorage
     */
    applyTheme(theme, saveToStorage = false) {
        const validTheme = [this.LIGHT, this.DARK].includes(theme) ? theme : this.LIGHT;
        document.documentElement.setAttribute('data-theme', validTheme);

        if (saveToStorage) {
            this.isManualOverride = true;
            try {
                localStorage.setItem(this.THEME_KEY, validTheme);
            } catch (e) {
                console.warn('[Theme] Could not save theme preference:', e);
            }
        }
    }

    /**
     * Toggle between light and dark theme (manual user action)
     */
    toggle() {
        const currentTheme = document.documentElement.getAttribute('data-theme') || this.getSystemTheme();
        const newTheme = currentTheme === this.DARK ? this.LIGHT : this.DARK;
        this.applyTheme(newTheme, true); // true = save to localStorage
        return newTheme;
    }

    /**
     * Clear manual preference and return to system preference
     */
    clearManualPreference() {
        try {
            localStorage.removeItem(this.THEME_KEY);
            this.isManualOverride = false;
            const systemTheme = this.getSystemTheme();
            this.applyTheme(systemTheme);
        } catch (e) {
            console.warn('[Theme] Could not clear preference:', e);
        }
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

        this._darkModeQuery = window.matchMedia('(prefers-color-scheme: dark)');

        // Listen for changes in system preference
        this._darkModeQuery.addEventListener('change', (e) => {
            const newTheme = e.matches ? this.DARK : this.LIGHT;
            this.isManualOverride = false;
            try {
                localStorage.removeItem(this.THEME_KEY);
            } catch (err) {
                console.warn('[Theme] Could not clear localStorage on system change:', err);
            }
            this.applyTheme(newTheme, false);
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
