import { render } from '@testing-library/react';
import { useContext } from 'react';
import AuthContext, { AuthContextProvider } from './auth-context';
import { LsTokenService } from '../hooks/UseLsToken';
import { Token } from '../util/Token';

const tokenTimestamp = (expiresIn: number) => new Date().getTime() + expiresIn;

describe('AuthContext', () => {
    it('Should provide auth context', async () => {
        //given
        LsTokenService.saveToken(new Token("test", tokenTimestamp(1000)))

        const TestComponent: React.FC = () => {
            const {autoLogoutIn} = useContext(AuthContext)
            return (<div data-value={autoLogoutIn}>
                test
            </div>)
        }

        const { findByText } = render(<AuthContextProvider>
            <TestComponent/>
        </AuthContextProvider>);

        //when
        const div = await findByText("test");

        //then
        expect(Number.parseInt(div.dataset.value!)).toBeLessThan(1000)
        expect(Number.parseInt(div.dataset.value!)).toBeGreaterThan(0)
    });
});