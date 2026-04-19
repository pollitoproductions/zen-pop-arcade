#!/bin/bash
# Security setup script for Android project
# Prevents accidental commits of keystores and sensitive data

set -e

ANDROID_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "Setting up security for Android project..."
echo ""

# Update .gitignore to exclude sensitive files
echo "Updating .gitignore to protect sensitive files..."
if [ -f "$ANDROID_DIR/.gitignore" ]; then
    # Append security rules if not already present
    if ! grep -q "keystore.jks" "$ANDROID_DIR/.gitignore"; then
        cat "$ANDROID_DIR/.gitignore-secrets" >> "$ANDROID_DIR/.gitignore"
        echo "✓ Added security rules to .gitignore"
    else
        echo "✓ Security rules already in .gitignore"
    fi
else
    echo "⚠ Warning: .gitignore not found"
fi

# Remove any existing keystores from git history (if they were added)
echo ""
echo "Checking for accidentally committed keystores..."
if git -C "$ANDROID_DIR" ls-files | grep -q "\.jks\|\.keystore"; then
    echo "⚠ Found keystore files in git!"
    echo ""
    echo "To remove them from history, run:"
    echo "  git rm --cached app/src/main/resources/*.jks"
    echo "  git rm --cached *.keystore"
    echo "  git commit -m 'Remove sensitive files'"
else
    echo "✓ No keystores found in git"
fi

echo ""
echo "✓ Security setup complete!"
echo ""
echo "Remember:"
echo "  1. Never commit keystore.jks to version control"
echo "  2. Don't share passwords in code or messages"
echo "  3. Back up keystore.jks in a secure location"
echo "  4. Use environment variables for production passwords"
echo ""
