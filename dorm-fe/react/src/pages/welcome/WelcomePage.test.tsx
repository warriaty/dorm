import React from 'react';
import { render } from '@testing-library/react';
import WelcomePage from './WelcomePage';

test('renders without crashing', () => {
    const { baseElement } = render(<WelcomePage />);
    expect(baseElement).toBeDefined();
});
