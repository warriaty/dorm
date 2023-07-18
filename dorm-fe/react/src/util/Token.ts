export class Token implements FeToken {
    constructor(
        public readonly value: string,
        public readonly expirationTimestamp: number
    ) {
    }

    static isFeToken(obj: unknown): obj is FeToken {
        if (obj && typeof obj === 'object') {
            return 'value' in obj && typeof obj.value === 'string'
                && 'expirationTimestamp' in obj && typeof obj.expirationTimestamp === 'number';
        }
        return false;
    }

    expiresIn(): number {
        return Token.parseUtcTimestamp(this.expirationTimestamp).getTime() - new Date().getTime();
    }

    static parseUtcTimestamp(utcString: number): Date {
        return new Date(+utcString);
    }
}

export interface BeToken {
    tokenValue: string,
    expirationTimestamp: number
}

export interface FeToken {
    value: string,
    expirationTimestamp: number
}