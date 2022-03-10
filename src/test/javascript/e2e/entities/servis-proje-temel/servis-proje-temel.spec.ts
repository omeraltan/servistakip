import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServisProjeTemelComponentsPage from './servis-proje-temel.page-object';
import ServisProjeTemelUpdatePage from './servis-proje-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('ServisProjeTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let servisProjeTemelComponentsPage: ServisProjeTemelComponentsPage;
  let servisProjeTemelUpdatePage: ServisProjeTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    servisProjeTemelComponentsPage = new ServisProjeTemelComponentsPage();
    servisProjeTemelComponentsPage = await servisProjeTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load ServisProjeTemels', async () => {
    expect(await servisProjeTemelComponentsPage.title.getText()).to.match(/Servis Proje Temels/);
    expect(await servisProjeTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServisProjeTemels', async () => {
    const beforeRecordsCount = (await isVisible(servisProjeTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(servisProjeTemelComponentsPage.table);
    servisProjeTemelUpdatePage = await servisProjeTemelComponentsPage.goToCreateServisProjeTemel();
    await servisProjeTemelUpdatePage.enterData();
    expect(await isVisible(servisProjeTemelUpdatePage.saveButton)).to.be.false;

    expect(await servisProjeTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(servisProjeTemelComponentsPage.table);
    await waitUntilCount(servisProjeTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await servisProjeTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await servisProjeTemelComponentsPage.deleteServisProjeTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(servisProjeTemelComponentsPage.records, beforeRecordsCount);
      expect(await servisProjeTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(servisProjeTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
