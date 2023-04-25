import axios, { AxiosInstance } from 'axios';
import { LsTokenService } from '../hooks/UseLsToken';

const httpService: AxiosInstance = axios.create({ baseURL: process.env.REACT_APP_BE_URL });

httpService.interceptors.request.use(LsTokenService.authInterceptor);

export default httpService;
