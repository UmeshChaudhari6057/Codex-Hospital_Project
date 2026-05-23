# Day 4 Frontend Layout Roadmap

The current Angular app already has a working single-page shell in `app.html` and `app.scss`.

Day 4 will evolve this into a cleaner portal layout.

## Current Layout

- Sidebar navigation
- Topbar with page title and status
- Overview metrics
- Patient form section
- Doctor form section
- Appointment form section
- Latest API response panel

## Target Layout Components

Planned structure:

```text
src/app/core/layout/
├── app-layout.component.ts
├── app-layout.component.html
├── app-layout.component.scss
├── header/
│   ├── header.component.ts
│   ├── header.component.html
│   └── header.component.scss
├── sidebar/
│   ├── sidebar.component.ts
│   ├── sidebar.component.html
│   └── sidebar.component.scss
└── footer/
    ├── footer.component.ts
    ├── footer.component.html
    └── footer.component.scss
```

## Day 4 Tasks

- Extract sidebar from root app template.
- Extract header/topbar from root app template.
- Add footer with environment and version information.
- Keep mobile responsive behavior.
- Keep all existing API forms working.

## Design Goals

- Hospital dashboard style.
- Clean navigation.
- Responsive layout for tablet and mobile.
- Easy expansion for auth, patient, doctor, and appointment feature modules.

## Notes

This roadmap is added before component extraction so the build remains stable while the UI is refactored step by step.
