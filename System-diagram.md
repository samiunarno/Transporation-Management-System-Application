┌─────────┐    ┌────────────┐    ┌─────────────┐
│  User   │    │ UserLock   │    │  Transport  │
├─────────┤    ├────────────┤    ├─────────────┤
│ -username│    │ -username  │    │ -id         │
│ -password│    │ -isLocked  │    │ -name       │
│ -role    │    │ -unlockKey │    │ -capacity   │
└─────────┘    └────────────┘    └─────────────┘
      │              │                  │
      └──────────────┼──────────────────┘
                     │
         ┌───────────┴──────────┐
         │                      │
┌─────────────────┐    ┌─────────────────┐
│   AuthService   │    │ TransportService│
├─────────────────┤    ├─────────────────┤
│ +login()        │    │ +addTransport() │
│ +register()     │    │ +removeTransport│
│ +logout()       │    │ +findTransport()│
└─────────────────┘    └─────────────────┘