import {CounterConstant} from '../common/Constant';

export default function counter(state = {count: 0}, action) {
    switch (action.type) {
        case CounterConstant.INCREMENT:
            state.count++
            return state
        case 'DECREMENT':
            state.count--;
            return state
        case 'Clean':
            state.count = 0;
            return state;
        default:
            return state
    }
}