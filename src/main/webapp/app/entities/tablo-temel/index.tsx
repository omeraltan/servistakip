import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TabloTemel from './tablo-temel';
import TabloTemelDetail from './tablo-temel-detail';
import TabloTemelUpdate from './tablo-temel-update';
import TabloTemelDeleteDialog from './tablo-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TabloTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TabloTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TabloTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={TabloTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TabloTemelDeleteDialog} />
  </>
);

export default Routes;
