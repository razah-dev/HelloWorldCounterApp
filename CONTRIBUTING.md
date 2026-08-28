### How to start a new feature branch safely:
```bash
git stash
git fetch origin
git switch -c <branch-name> origin/main
git stash pop
```

Or, simply the following if you do not have any local changes:
```bash
git fetch origin
git switch -c <branch-name> origin/main
```

