kontrol
===

> Control theory experiments in Kotlin, for FRC

## What's Control Theory?
Read the article on [Wikipedia](https://en.wikipedia.org/wiki/Control_theory).

## Features
 - [x] State space model definition
 - [x] Discretization of continous models
 - [x] State space controller definition
 - [x] Controller simulation (outputs a string for [Desmos](https://www.desmos.com/calculator))
 - [ ] Pole placement gains generation
 - [ ] LQR controller design
 - [ ] State space estimator definition
 - [ ] Reference tracking

## What's State Space Control?
IMO, the StateSpaceControl for Arduino project has [a pretty good explanation](https://github.com/tomstewart89/StateSpaceControl/blob/master/WhatIsStateSpaceControl.md).

Another resource, specifically made for the FRC competition, is given here - [Practical Guide to State-space Control](https://file.tavsys.net/control/state-space-guide.pdf).

## Inspiration and other libraries
 - [StateSpaceControl](https://github.com/tomstewart89/StateSpaceControl/) - A state space control library for Arduino, written in C++.
 - [muan/control](https://github.com/frc1678/robot-code-public/tree/master/muan/control) - FRC team 1678's control library, written in C++ and Python.
 - [frccontrol](https://github.com/calcmogul/frccontrol) - A state space control library written in Python by the author of the Practical Guide to State-space Control for FRC usage.
 - [FRC 971's Software Archives](http://frc971.org/content/2018-software) - FRC team 971's code archives, up until the 2018 season. Has some libraries written in Python and C++ for state-space control.
