import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServisProjeTemel from './servis-proje-temel';
import ServisProjeTemelDetail from './servis-proje-temel-detail';
import ServisProjeTemelUpdate from './servis-proje-temel-update';
import ServisProjeTemelDeleteDialog from './servis-proje-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServisProjeTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServisProjeTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServisProjeTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServisProjeTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServisProjeTemelDeleteDialog} />
  </>
);

export default Routes;
