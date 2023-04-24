import { useState } from 'react';

export const useDebounceState = <S>(initialState: S, debounceTime: number): [S, (e: S) => void] => {
    const [state, setState] = useState(initialState);

    let debounceTimer = setTimeout(() => {
        setState(initialState)
    }, debounceTime);

    const resetDebounceTimer = (e: S) => {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            setState(e)
        }, debounceTime);
    };

    return [state, resetDebounceTimer];
};