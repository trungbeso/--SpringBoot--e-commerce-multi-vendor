package com.benjamin.domain;

public enum EAccountStatus {
    PENDING_VERIFICATION,                   /// Account is created but not yet verified
    ACTIVE,                                 /// Account is active and in good standing
    SUSPENDED,                              /// Account id temporarily suspended, possibly due to violations
    DEACTIVATED,                            /// Account is deactivated, user may have chosen to deactivate it
    CLOSED,                                 /// Account is permanently banned due to server violations
    BANNED,                                 /// Account is permanently closed, possibly at user request
}
