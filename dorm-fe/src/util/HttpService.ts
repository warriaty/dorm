import axios from 'axios';
import { LsTokenService } from '../hooks/UseLsToken';

const httpService = axios.create({ baseURL: process.env.REACT_APP_BE_URL });

httpService.interceptors.request.use((config) => {
    const token = LsTokenService.fetchToken();
    if (token && !config.headers.Authorization ) {
        config.headers.Authorization = 'Bearer ' + token.value
    }
    return config;
});

export default httpService;
