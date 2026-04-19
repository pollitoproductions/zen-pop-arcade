#!/bin/bash
# Icon Generation Script for Zen Pop Arcade
#
# Usage: ./generate_icons.sh <path_to_source_icon.png>
#
# This script resizes your icon to all required Android densities
# and places them in the correct directories.
#
# Requirements: ImageMagick (convert command)

if [ -z "$1" ]; then
    echo "Usage: $0 <path_to_icon.png>"
    echo ""
    echo "Example: $0 ~/my-icon.png"
    exit 1
fi

if [ ! -f "$1" ]; then
    echo "Error: File not found: $1"
    exit 1
fi

SOURCE_ICON="$1"
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Define densities and sizes
declare -A DENSITIES=([mdpi]=48 [hdpi]=72 [xhdpi]=96 [xxhdpi]=144 [xxxhdpi]=192)

# Check if convert is available
if ! command -v convert &> /dev/null; then
    echo "Error: ImageMagick 'convert' command not found."
    echo "Install with: sudo apt-get install imagemagick (Linux)"
    echo "             brew install imagemagick (macOS)"
    exit 1
fi

echo "Generating Android app icons from: $SOURCE_ICON"

# Generate icons for each density
for density in "${!DENSITIES[@]}"; do
    size=${DENSITIES[$density]}
    output_dir="$SCRIPT_DIR/app/src/main/res/mipmap-$density"
    output_file="$output_dir/ic_launcher.png"
    
    mkdir -p "$output_dir"
    
    echo "  Generating $density ($size×$size)..."
    convert "$SOURCE_ICON" -resize "${size}x${size}!" "$output_file"
    
    if [ $? -eq 0 ]; then
        echo "    ✓ Created: $output_file"
    else
        echo "    ✗ Failed to create: $output_file"
        exit 1
    fi
done

echo ""
echo "✓ Successfully generated all app icons!"
echo ""
echo "Next steps:"
echo "1. Review the generated icons in app/src/main/res/mipmap-*/"
echo "2. Build the app: ./gradlew bundleRelease"
echo "3. Upload to Google Play Console"
