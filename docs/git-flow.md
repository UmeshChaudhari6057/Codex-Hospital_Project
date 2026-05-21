# Git Flow Strategy

This project uses a simple feature-branch workflow so backend, frontend, documentation, and deployment work stay organized.

## Main Branches

- `main` - stable branch. Only tested and reviewed work should be merged here.
- `feature/day-N` - daily development branches from the 50-day plan.

## Daily Branch Pattern

Use this naming style:

```text
feature/day-1
feature/day-2
feature/day-3
```

For specific work inside a day, use:

```text
feature/day-3-angular-foundation
feature/day-5-auth-ui
feature/day-23-redis-cache
```

## Recommended Daily Workflow

```bash
git checkout main
git pull origin main
git checkout -b feature/day-1
```

After making changes:

```bash
git status
git add .
git commit -m "Day 1: project setup"
git push origin feature/day-1
```

Then create a Pull Request from `feature/day-1` into `main`.

## Commit Message Format

Use clear commits:

```text
Day 1: add development checklist
Day 3: initialize Angular frontend
Day 5: add login form
Day 23: configure Redis cache
```

## Pull Request Checklist

Before merging:

- Build passes locally.
- New documentation is updated.
- No secrets are committed.
- `.env` files are ignored.
- Screenshots are added for UI changes.
- API changes are documented.

## Notes

Do not work directly on `main` for planned development tasks. Use one branch per day or one branch per feature.
