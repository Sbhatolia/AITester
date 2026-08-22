# Taste

## Tooling & environment
- Works on Windows in a cmd/PowerShell environment with Git (prompts show `C:\Users\...` and PowerShell/cmd usage); expects shell commands, diagnostics, and fixes to be Windows-compatible (e.g., `dir`, `rmdir /s /q`, PowerShell cmdlets) rather than Unix-only. Confidence: 0.7

## Workflow & communication
- Uses Git with GitHub as the remote host and gives terse, high-level instructions (e.g., "now do git commit <repo-url>", "I have saved the files now push"), expecting the assistant to handle the full workflow autonomously — checking remotes, adding the remote, writing the commit message, and pushing — without being asked for each step. Confidence: 0.6
