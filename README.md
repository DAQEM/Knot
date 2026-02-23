### A unified API tying your cross-platform mods together.

**Knot** is a lightweight multi-loader API designed to bridge the gap between **Fabric** and **NeoForge**. Instead of fraying your nerves managing multiple codebases, Knot abstracts loader-specific complexities so you can write your code once and deploy it everywhere.

Designed for stability and simplicity, **Knot** securely ties your project together without the bloat.

## 🧵 Features

* **Unified Registration:** Register Blocks, Items, Entities, and Fluids using a single common syntax.
* **Networking Abstraction:** Handle packets and client-server communication seamlessly across loaders.
* **Event Handling:** A standardized event bus that hooks into loader-specific lifecycles without getting tangled.
* **Minimal Overhead:** Knot is designed to be a thin layer, ensuring maximum performance and compatibility.

## 🔧 For Developers

To start using Knot in your workspace, add the following to your `build.gradle`:

```groovy
repositories {
    maven { url "https://maven.daqem.com/releases" }
}

dependencies {
    // Replace with the latest version
    modImplementation "com.daqem.knot:knot-common:<version>"
}

```

Check out the **[Official Wiki](https://daqem.com/projects/knot)** for documentation and example templates.

## 🎮 For Players

Knot is a **library mod**. It does not add content on its own, but it is required for many other mods to function correctly. Fret knot, installation is simple:

* **If you are a player:** Simply download the version matching your game loader (Fabric/NeoForge) and place it in your `mods` folder.
* **If you are a modpack creator:** You are free to include Knot in any modpack.

## 🐛 Issues & Support

Found a bug? Have a suggestion?

* Report issues on our **[GitHub Tracker](https://github.com/DAQEM/Knot)**.
* Join our **[Discord Community](https://daqem.com/discord)** for development support.