#!/bin/bash

# 🌙 Ramadan Kareem 2026 - Privacy Policy Deployment Script
# This script helps deploy the privacy policy to GitHub Pages

echo "🚀 Deploying Privacy Policy to GitHub Pages..."

# Check if we're in the right directory
if [ ! -d "docs" ]; then
    echo "❌ Error: docs directory not found. Please run this from the project root."
    exit 1
fi

# Navigate to docs directory
cd docs

# Check if GitHub CLI is available
if command -v gh &> /dev/null; then
    echo "📦 Using GitHub CLI..."
    
    # Create a new branch for gh-pages if it doesn't exist
    if ! git show-ref --verify --quiet refs/heads/gh-pages; then
        git checkout --orphan gh-pages
        git rm -rf .
        echo "# Ramadan Kareem 2026 - Privacy Policy" > README.md
        git add .
        git commit -m "Initial gh-pages setup"
    else
        git checkout gh-pages
    fi
    
    # Copy all files from docs to root
    cp -r * . 2>/dev/null || true
    
    # Add and commit changes
    git add .
    git commit -m "Update privacy policy - $(date)"
    
    # Push to GitHub
    git push origin gh-pages
    
    echo "✅ Privacy Policy deployed successfully!"
    echo "🌐 Live at: https://mohammad-asif-zafar.github.io/Ramadan-Kareem-2026/"
    
else
    echo "⚠️  GitHub CLI not found. Manual deployment required:"
    echo ""
    echo "1. Push your code to GitHub"
    echo "2. Go to repository Settings → Pages"
    echo "3. Select 'Deploy from a branch'"
    echo "4. Choose 'gh-pages' branch and '/ (root)' folder"
    echo "5. Save and wait for deployment"
fi

echo ""
echo "📋 Next Steps:"
echo "1. Add the privacy policy URL to Play Console"
echo "2. Update the app with the privacy policy link"
echo "3. Test the live privacy policy URL"
