import axios from 'axios';

describe('Should create HttpService component', () => {
    it('Should create axios with base url', () => {
        //given
        const create = jest.spyOn(axios, 'create')

        //when
        require('./HttpService');

        //then
        expect(create).toHaveBeenCalledWith({baseURL: process.env.REACT_APP_BE_URL});
    });
});