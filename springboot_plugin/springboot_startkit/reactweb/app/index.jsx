import React from 'react'
import ReactDOM from 'react-dom'

import {createStore, combineReducers, applyMiddleware} from 'redux'
import {Provider} from 'react-redux'
import {Router, Route, browserHistory,IndexRoute} from 'react-router'
import {syncHistoryWithStore, routerReducer} from 'react-router-redux'

import counter from './reducers/CountReducer'

import App from './pages/App';
import About from './pages/About';
import Info from './pages/Info';

const store = createStore(
    combineReducers({
        counter,
        routing: routerReducer
    })
);

const history = syncHistoryWithStore(browserHistory, store)
history.listen(location => console.log(location.pathname));

var rootEl = document.createElement('div');
document.body.appendChild(rootEl);

function render() {
    ReactDOM.render(
        <Provider store={store}>
            <Router history={history}>
                <Route path="/" component={App}>
                    <IndexRoute component={Info}/>
                    <Route path="foo" component={Info}/>
                    <Route path="bar" component={About}/>
                </Route>
            </Router>
        </Provider>
        , rootEl
    )
}

render();
store.subscribe(render)