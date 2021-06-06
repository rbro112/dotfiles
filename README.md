# Ryan's dotfiles

My dotfiles setup, catered towards mobile development (Android, iOS).

Heavily inspired by:
- https://github.com/holman/dotfiles/
- https://github.com/mathiasbynens/dotfiles

## Setup

```bash
git clone https://github.com/rbro112/dotfiles.git && cd dotfiles && ./scripts/setup
```

To update, cd into the dotfiles directory and then:

```bash
./scripts/setup
```

Once setup, it's good to go! No need to run this setup script again, any updates

#### Install

`./scripts/install` will run all `install.sh` scripts found in any topic folder. This is run automatically with `./scripts/setup`, but can be run standalone with

```bash
./scripts/install
```

## Left to do

- Fix prompt to update Git repo when navigating around.
